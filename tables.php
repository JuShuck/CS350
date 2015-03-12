<!DOCTYPE html>
<html>
<head>

</head>
<body>
<pre><?php
error_reporting(E_ALL | E_STRICT);
ini_set('display_errors', 1);

//define ('RESULTS_DIR', './results');
define ('HIDE_OUTPUT', true);
require ('index.php');

$entries = get_result_entries();

echo '
<style type="text/css">
	table tr td { text-align: center; }
</style>
<p><a href="index.php">Go back..</a></p>';

foreach ($entries as $sort_type => $config_types)
{
	foreach ($config_types as $config_type => $data_types)
	{
		echo '<h3>', $sort_type, (strlen($config_type) > 0 ? ' ('. $config_type. ')' : ''), '</h3>';
		
		
		echo '<table>';
		
		
		foreach ($data_types as $data_type => $results)
		{
			echo '
			<tr>
				<td style="text-align: left !important;"><strong>', $data_type, '</strong></td>
				<td style="width: 50px;"><em>O(n)</em></td>
				<td style="width: 100px;"><em>O(n log(n))</em></td>
				<td style="width: 80px;"><em>O(n<sup>2</sup>)</em></td>
			</tr>';
			
			foreach ($results as $result)
			{
				$basicOpCount = get_basic_ops($result['dir']);
				
				echo '
			<tr>
				<td style="text-align: left !important;">', number_format($result['size']), '</td>
				<td>', isON($basicOpCount, $result['size']), '</td>
				<td>', isONLOGN($basicOpCount, $result['size']), '</td>
				<td>', isON2($basicOpCount, $result['size']), '</td>
			</tr>';
			}
		}
		
		echo '</table>';
		
		//print_r($data_types);
	}
}

//print_r($entries);


// ---------------------------------------------------------------------
function isON($count, $n)
{
	if ($count <= 0)
	{
		return 'N/A';
	}
	
	return ($count <= $n) ? 'Yes' : 'No';
}

function isONLOGN($count, $n)
{
	if ($count <= 0)
	{
		return 'N/A';
	}
	
	return ($count <= ($n * log($n, 2))) ? 'Yes' : 'No';
}

function isON2($count, $n)
{
	if ($count <= 0)
	{
		return 'N/A';
	}
	
	return ($count <= pow($n, 2)) ? 'Yes' : 'No';
}

function get_basic_ops($dir, $which = 'average')
{
	static $cache = array();
	
	if (isset($cache[$dir]) && isset($cache[$dir][$which]))
	{
		return $cache[$dir][$which];
	}
	
	$files = scandir(RESULTS_DIR. '/'. $dir);
	
	$filename = null;
	foreach ($files as $file)
	{
		if (strpos($file, 'less-3-std-dev') === false)
		{
			continue;
		}
		
		$filename = $file;
	}
	
	if ($filename == null)
	{
		return -1;
	}
	
	$csv = get_csv_file(RESULTS_DIR. '/'. $dir. '/'. $filename);
	
	$KEY = 'basic op count';
	$max = (int)$csv[0][$KEY];
	$min = (int)$csv[0][$KEY];
	$sum = 0;
	$total = 0;
	foreach ($csv as $row)
	{
		$count = (int)$row[$KEY];
		
		$max = max($max, $count);
		$min = min($min, $count);
		$sum = $sum + $count;
		$total++;
	}
	
	$cache[$dir] = array(
		'min' => $min,
		'max' => $max,
		'average' => $sum / $total,
	);
	
	return $cache[$dir][$which];
}

function get_csv_file($filename)
{
	$contents = file_get_contents($filename);
	$lines = explode("\n", $contents);
	
	return get_csv(explode(",", $lines[0]), $lines);
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