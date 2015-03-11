<?php
define ('RESULTS_DIR', './results');

if (!defined('HIDE_OUTPUT'))
{
	echo '<pre>';

	echo '<p><a href="avg.php">Click here for more cool graphs...</a></p>';
	echo '<p><a href="tables.php">Look here! TABLES!</a></p>';
	
	$entries = get_result_entries();

	foreach ($entries as $sort_type => $configs)
	{
		echo '<p><strong>', $sort_type, '</strong></p>';
		
		foreach ($configs as $config_name => $data_types)
		{
			echo '<p style="margin-left: 15px;"><em>', (strlen($config_name) == 0 ? 'N/A' : $config_name), '</em></p>';
			
			foreach ($data_types as $data_type => $results)
			{
				echo '<p style="margin-left: 30px;"><strong>', $data_type, '</strong></p>';
				
				foreach ($results as $result)
				{
					echo '<p style="margin-left: 45px;"><a href="chart.php?dir=', urlencode($result['dir']), '">', number_format($result['size']), '</a></p>';
				}
			}
		}
	}

	echo '</pre>';
}

function get_result_entries()
{
	if (file_exists('./entry-cache.arr'))
	{
		return @unserialize(file_get_contents('./entry-cache.arr'));
	}
	
	$entries = array();
	$directories = scandir(RESULTS_DIR);
	
	foreach ($directories as $directory)
	{
		if ($directory == '.' || $directory == '..')
		{
			continue;
		}
		
		$entries = add_entry(RESULTS_DIR. '/'. $directory, $entries);
	}
	
	return $entries;
}

function add_entry($directory, $entries)
{
	$output = file_get_contents($directory. '/output.log');
	
	if ($output == null)
	{
		return $entries;
	}
	
	$output = explode("\n", $output);
	
	$want = array('sort name', 'sort config', 'data type', 'data size');
	
	$entry = array();
	for ($line = 0; $line < 15; $line++)
	{
		$text = trim($output[$line]);
		
		if (strlen($text) == 0 || strpos($text, ':') === false)
		{
			continue;
		}
		
		list ($label, $value) = explode(':', $text, 2);
		
		$label = trim(strtolower($label));
		
		if (!in_array($label, $want))
		{
			continue;
		}
		
		$entry[$label] = trim($value);
	}
	
	if (!array_key_exists($entry['sort name'], $entries))
	{
		$entries[$entry['sort name']] = array();
	}
	
	if (!array_key_exists($entry['sort config'], $entries[$entry['sort name']]))
	{
		$entries[$entry['sort name']][$entry['sort config']] = array();
	}
	
	if (!array_key_exists($entry['data type'], $entries[$entry['sort name']][$entry['sort config']]))
	{
		$entries[$entry['sort name']][$entry['sort config']][$entry['data type']] = array();
	}
	
	$entries[$entry['sort name']][$entry['sort config']][$entry['data type']][] = array(
		'size' => $entry['data size'],
		'dir' => basename($directory),
	);
	
	return $entries;
}