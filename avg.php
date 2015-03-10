<!DOCTYPE html>
<html>
<head>
	<title>Best, Worst, Average Mega Charts of Cool</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>
</head>
<body>
<pre><?php
define('HIDE_OUTPUT', true);
require ('index.php');
define ('RESULTS_DIR', './results');

$size = !empty($_GET['size']) ? (int)$_GET['size'] : 0;
$type = !empty($_GET['type']) ? $_GET['type'] : '';

$sizes = get_size_entries();

if (!isset($sizes[$size]) || !isset($sizes[$size][$type]))
{
	echo '<p><a href="index.php">Go back to index</a></p>';
	echo '<p><strong>Select a data set size:</strong></p>';
	
	foreach ($sizes as $size => $types)
	{
		echo '<p style="margin-left: 15px;">', $size, '</p>';
		
		foreach ($types as $type => $entries)
		{
			echo '<p style="margin-left: 30px;"><a href="avg.php?size=', $size, '&amp;type=', $type, '">', $type, '</a></p>';
			
			foreach ($entries as $entry)
			{
				echo '<p style="margin-left: 45px;"><em>', $entry['sort_type'], (strlen($entry['config_type']) > 0 ? ' ('. $entry['config_type']. ')' : '') , '</em></p>';
			}
		}
	}
}
else
{
	echo '
	<p><a href="avg.php">Go Back to data size selection</a></p>
<div id="chart" style="width: 100%; height: 500px;"></div>';

	$chartObj = get_chart_obj($sizes, $size, $type);

	echo '<script type="text/javascript">
		$(function()
		{
			$("#chart").highcharts(', json_encode($chartObj), ');
		});
	</script>';
}


// Begin functions, end output
function get_size_entries()
{
	$entries = get_result_entries();
	$sizes = array();
	
	foreach ($entries as $sort_type => $configs)
	{
		foreach ($configs as $config_type => $datas)
		{
			foreach ($datas as $data_type => $results)
			{
				foreach ($results as $result)
				{
					if (!isset($sizes[$result['size']]))
					{
						$sizes[$result['size']] = array();
					}
					
					if (!isset($sizes[$result['size']][$data_type]))
					{
						$sizes[$result['size']][$data_type] = array();
					}
					
					$sizes[$result['size']][$data_type][] = array(
						'sort_type' => $sort_type,
						'config_type' => $config_type,
						'dir' => $result['dir'],
					);
				}
			}
		}
	}
	
	return $sizes;
}

//Basic statistics with first 3 rows & std dev removed:
function get_chart_obj($sizes, $size, $type)
{
	$entries = $sizes[$size][$type];
	
	$stats = array();
	foreach ($entries as $entry)
	{
		$stats[] = get_stats_entry($entry);
	}
	
/*
{

        chart: {
            type: 'column'
        },

        title: {
            text: 'Total fruit consumtion, grouped by gender'
        },

        xAxis: {
            categories: ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas']
        },

        yAxis: {
            allowDecimals: false,
            min: 0,
            title: {
                text: 'Number of fruits'
            }
        },

        tooltip: {
            formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    'Total: ' + this.point.stackTotal;
            }
        },

        series: [{
            name: 'John',
            data: [5, 3, 4, 7, 2]
        }, {
            name: 'Joe',
            data: [3, 4, 4, 2, 5]
        }, {
            name: 'Jane',
            data: [2, 5, 6, 2, 1]
        }, {
            name: 'Janet',
            data: [3, 0, 4, 4, 3]
        }]
    }
*/
	
	return array(
		'chart' => array(
			'type' => 'column',
		),

        'title' => array(
			'text' => 'Fastest, Average and Slowest Time',
        ),
        
       'subtitle' => array(
			'text' => get_subtitle($stats),
		),

        'xAxis' => array(
            'categories' => get_categories($stats),
        ),

        'yAxis' => array(
            'allowDecimals' => false,
            'min' => 0,
            'title' => array(
                'text' => 'Time (ns)'
            )
        ),

        'series' => array(
			array(
				'name' => 'Slowest',
				'data' => get_all($stats, 'slowest'),
			),
			array(
				'name' => 'Average',
				'data' => get_all($stats, 'average'),
			),
			array(
				'name' => 'Fastest',
				'data' => get_all($stats, 'fastest'),
			),
        )
    );
}

function get_subtitle($entries)
{
	$names = array();
	
	foreach ($entries as $entry)
	{
		$name = $entry['sort_type'];
		
		if (strlen($entry['config_type']) > 0)
		{
			$name .= ' ('. $entry['config_type']. ')';
		}
		
		$names[] = $name;
	}
	
	return implode(', ', $names);
}

function get_all($entries, $key)
{
	$data = array();
	
	foreach ($entries as $entry)
	{
		$data[] = (double)$entry[$key];
	}
	
	return $data;
}

function get_categories($stats)
{
	$arr = array();
	
	foreach ($stats as $entry)
	{
		$arr[] = $entry['sort_type']. (strlen($entry['config_type']) > 0 ? ' ('. $entry['config_type']. ')' : '');
	}
	
	return $arr;
}

function get_stats_entry($entry)
{
	$stats = get_stats_from(RESULTS_DIR. '/'. $entry['dir']. '/output.log');
	
	$stats['sort_type'] = $entry['sort_type'];
	$stats['config_type'] = $entry['config_type'];
	
	return $stats;
}

function get_stats_from($log)
{
	if (!file_exists($log))
	{
		echo '<p style="color: red;">Woops! I could not find the output.log file: ', $log, '</p>';
		return array();
	}
	
	$content = file_get_contents($log);
	$lines = explode("\n", $content);
	
	$data = array();
	$copy = false;
	foreach ($lines as $line)
	{
		$line = trim($line);
		
		if ($line == 'Basic statistics with first 3 rows & std dev removed:')
		{
			$copy = true;
			continue;
		}
		else if (!$copy)
		{
			continue;
		}
		
		$data[] = $line;
	}
	
	if ($copy == false || count($data) == 0)
	{
		echo '<p style="color: red;">Woops! I could not find the statistics in file: ', $log, '</p>';
		return array();
	}
	
	/*            [2] => Total Elapsed Time (sec):	5.853941430000001
            [3] => Average Elapsed Time (ns):	5877451.234939759
            [4] => Average Elapsed Time (sec):	0.005877451234939759
            [5] => Fastest Run (ns):		3776943
            [6] => Fastest Run (sec):		0.003776943
            [7] => Slowest Run (ns):		57007882*/
            
	$want = array(
		'average elapsed time (ns)' => 'average',
		'fastest run (ns)' => 'fastest',
		'slowest run (ns)' => 'slowest',
	);
	
	$stats = array();
	foreach ($data as $line)
	{
		if (strpos($line, ':') === false)
		{
			continue;
		}
		
		list($key, $value) = explode(':', $line, 2);
		
		$key = trim(strtolower($key));
		
		if (!isset($want[$key]))
		{
			continue;
		}
		
		$stats[$want[$key]] = (double)trim($value);
	}
	
	if (count($stats) != count($want))
	{
		echo '<p style="color: red;">Woops! I did not find everything I need in: ', $log, '</p>';
		return array();
	}
	
	return $stats;
}
?></pre>
</body>
</html>