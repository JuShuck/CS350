<!DOCTYPE html>
<html>
<head>
	<title>Chart</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>
	<script type="text/javascript">
		function formatTooltip()
		{
			var y = this.y;
			
			var formattedY = (function()
				{
					this.value = y;
					return formatYAxis();
				})();
			
			return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + formattedY;
		}
		
		function formatBasicOp()
		{
			return 'hi';
		}
		
		function formatYAxis()
		{
			var seconds = Number(this.value) / (Math.pow(10, 9));
			
			var converted = { h: 0, m: 0, s: 0};
			
			if (seconds >= 3600)
			{
				var hours = Math.floor(seconds / 3600);
				seconds = seconds - (hours * 3600);
				
				converted.h = hours;
			}
			
			if (seconds >= 60)
			{
				var minutes = Math.floor(seconds / 60);
				seconds = seconds - (minutes * 60);
				
				converted.m = minutes;
			}
			
			converted.s = Math.floor(seconds);
			
			var arr = [];
			if (converted.h > 0)
			{
				arr.push(converted.h + 'h');
			}
			
			if (converted.m > 0)
			{
				arr.push(converted.m + 'm');
			}
			
			if (converted.s > 0)
			{
				arr.push(converted.s + 's');
			}
			
			if (converted.h == 0 && converted.m == 0 && converted.s < 1)
			{
				var ns = this.value;
				
				var label = null;
				if (ns >= 1000)
				{
					ns = ns / 1000;
					label = 'k';
					
					if (ns >= 1000)
					{
						ns = ns / 1000;
						label = 'm';
					}
				}
				
				return number_format(ns, 2) + (label != null ? label + ' ' : '') + 'ns';
			}
			
			return arr.join('');
		}
		
		function number_format(number, decimals, dec_point, thousands_sep) {

  number = (number + '')
    .replace(/[^0-9+\-Ee.]/g, '');
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
    s = '',
    toFixedFix = function (n, prec) {
      var k = Math.pow(10, prec);
      return '' + (Math.round(n * k) / k)
        .toFixed(prec);
    };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n))
    .split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '')
    .length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1)
      .join('0');
  }
  return s.join(dec);
}
	</script>
</head>
<body><pre>
<?php
error_reporting(E_ALL | E_STRICT);
ini_set('error_log', './log.log');
ini_set('display_errors', 1);

define ('RESULTS_DIR', './results');
body();
function body()
{
// start

$dir = !empty($_GET['dir']) ? $_GET['dir'] : null;
$dir = basename($dir);

if ($dir == null)
{
	echo '<p><a href="index.php">Go back</a></p>';
	echo '<h1>No dir specified</h1>';
	return;
}

$dir = realpath(RESULTS_DIR. '/'. $dir);

if ($dir == false)
{
	echo '<p><a href="index.php">Go back</a></p>';
	echo '<h1>Invalid dir specified</h1>';
	return;
}

$file = empty($_GET['file']) ? null : trim(basename($_GET['file']));

$files = get_files($dir);

if ($file == null || !in_array($file, $files))
{
	list_files($dir);
	return;
}

$GLOBALS['chart_info'] = get_chart_info($dir);

do_chart($dir, $file);
// end
}

function get_chart_info($dir)
{
	$output = file_get_contents($dir. '/output.log');
	
	$lines = explode("\n", $output);
	
	$map = array();
	for ($i = 0; $i < 15; $i++)
	{
		$line = $lines[$i];
		
		if (strpos($line, ':') === false)
		{
			continue;
		}
		
		list($key, $value) = explode(':', $line, 2);
		
		$map[trim(strtolower($key))] = trim($value);
	}
	
	return $map;
}

function get_files($dir)
{
	$files = array();
	foreach (scandir($dir) as $file)
	{
		if (substr($file, -4, 4) != '.csv')
		{
			continue;
		}
		
		$files[] = $file;
	}
	
	return $files;
}

function list_files($dir)
{
	echo '<p><a href="index.php">Go back</a></p>';
	echo '<p><strong>Select a file to chart:</strong></p>';
	
	foreach (get_files($dir) as $file)
	{
		echo '<p><a href="', $_SERVER['PHP_SELF'], '?dir=', basename($dir), '&amp;file=', $file, '">', $file, '</a></p>';
	}
}

function do_chart($dir, $file)
{
	echo '<p><a href="', $_SERVER['PHP_SELF'], '?dir=', basename($dir), '">Go back</a></p>';

	$contents = file_get_contents($dir. '/'. $file);

	$lines = explode("\n", $contents);
	
	$header = explode(",", $lines[0]);
	$csv = get_csv($header, $lines);
	
	echo '<div id="chart" style="width: 100%; height: 500px;"></div>';
	
	echo '<script type="text/javascript">
		$(function()
		{
			var chart = ', json_encode(get_highcharts($csv)), ';
			//chart.yAxis[1].labels = { formatter: formatBasicOp };
			chart.yAxis.labels = { formatter: formatYAxis };
			chart.tooltip = { formatter: formatTooltip };
			
			$("#chart").highcharts(chart);
		});
	</script>';
	
	print_r($GLOBALS['chart_info']);
}

function get_highcharts($csv)
{
	global $chart_info;
	
	return array(
		'chart' => array(
			'type' => 'column',
		),
		'title' => array(
			'text' => $chart_info['sort name']. (strlen($chart_info['sort config']) > 0 ? ' ('. $chart_info['sort config']. ')' : ''),
		),
		'subtitle' => array(
			'text' => 'Data Set Type: '. $chart_info['data type']. ', Data Set Size: '. number_format($chart_info['data size']),
		),
		'yAxis' => array(
			//array(
				//'min' => 0,
				'title' => array(
					'text' => 'Time',
				),
				'type' => 'logarithmic',
				'plotLines' => array(
					array(
						'color' => 'red',
						'value' => get_average($csv, 'time (ns)'),
						'width' => 1,
						'zIndex' => 5000,
					)
				),
			//),
			/*array(
				'title' => array(
					'text' => 'Cumulative Basic Operations',
				),
				'type' => 'logarithmic',
				'opposite' => true,
			),*/
		),
		'series' => array(
			get_basic_series($csv),
			//get_cumulative_series($csv),
		),
	);
}

function get_cumulative_series($csv)
{
	$series = array(
		'name' => 'Basic Operations',
		'type' => 'spline',
		'yAxis' => 0,
		'data' => array(),
	);
	
	$sum = 0;
	foreach ($csv as $row)
	{
		$op = (int)$row['basic op count'];
		
		$sum += $op;
		$series['data'][] = $sum;
	}
	
	return $series;
}

function get_average($csv, $col)
{
	$sum = 0;
	foreach ($csv as $row)
	{
		if (!isset($row[$col]))
		{
			continue;
		}
		
		$sum += (int)$row[$col];
	}
	
	return $sum / (double)count($csv);
}

//define ('TIME_COL', 'time (ns)');
function get_basic_series($csv)
{
	$series = array(
		'name' => 'Iteration',
		//'type' => 'column',
		//'yAxis' => 0,
		'data' => array(),
	);
	
	foreach ($csv as $row)
	{
		$series['data'][] = (int)$row['time (ns)'];
	}
	
	return $series;
}

function get_csv($header, $lines)
{
	$header = trim_all($header);
	
	$count = count($lines);
	$csv = array();
	for ($i = 1; $i < $count; $i++)
	{
		if (trim($lines[$i]) == '')
		{
			continue;
		}
		
		$csv[] = map($header, explode(",", $lines[$i]));
	}
	
	return $csv;
}

function map($header, $row)
{
	$row = trim_all($row);
	
	$line = array();
	foreach ($header as $index => $label)
	{
		$line[strtolower($label)] = isset($row[$index]) ? $row[$index] : 'NOT SET';
	}
	
	return $line;
}

function trim_all($arr)
{
	$new = array();
	foreach ($arr as $entry)
	{
		$new[] = trim($entry);
	}
	
	return $new;
}
?></pre>
</body>
</html>