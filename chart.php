<!DOCTYPE html>
<html>
<head>
	<title>Chart</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>
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
			$("#chart").highcharts(', json_encode(get_highcharts($csv)), ');
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
			'min' => 0,
			'title' => array(
				'text' => 'Time (ns)',
			),
			'plotLines' => array(
				array(
					'color' => 'red',
					'value' => get_average($csv, 'time (ns)'),
					'width' => 1,
					'zIndex' => 5000,
				)
			),
		),
		'series' => array(
			get_basic_series($csv),
		),
	);
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
		'name' => 'Time (ns)',
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