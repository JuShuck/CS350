<?php
define ('CONFIG_DIR', './configs');

$sort_types = array(
	'Mergesort' => array(''),
	'Parallel Mergesort' => array(''),
	'Insertionsort' => array(''),
	'Quicksort' => array('Hoare', 'Lomuto'),
);
echo 'hi';
$data_types = array('Sorted', 'Reversed', 'UShaped', 'fewUnique', 'Random');
$data_set_sizes = array(100, 1000, 3000, 5000, 10000, 1000000);

$iterations = 1000;

foreach ($sort_types as $sort_type => $configs)
{
echo $sort_type, "\n";
	foreach ($configs as $config_type)
	{
		foreach ($data_types as $data_type)
		{
			foreach ($data_set_sizes as $data_set_size)
			{
				$file_name = write_config_file($sort_type, $config_type, $data_type, $data_set_size, $iterations);
				echo 'Made config: ', $file_name, "\r\n";
			}
		}
	}
}

function write_config_file($sort_type, $config_type, $data_type, $data_set_size, $iterations)
{
	$config_file = "[Sort]
; The name of the sort
; Options: { Mergesort, Parallel Mergesort, Insertionsort }
Name={$sort_type}

; Any extra configuration options to pass, Quicksort needs to know it's
; partition type, for example.
; Options:{ Hoare, Lomuto }
Config={$config_type}

[Data]
; Type of data to use (whatever the Test Data Generator allows)
; Options: { Sorted, Reversed, UShaped, fewUnique, Random}
Type={$data_type}

; Any configuration options to pass to the test data generator.
Config=

; The data set size to get from the Test Data Generator.
Size={$data_set_size}

; The number of times to sort the data set.
Iterations={$iterations}";

	$file_name = array($sort_type);
	
	if ($config_type != null && strlen($config_type) > 0)
	{
		$file_name[] = $config_type;
	}
	
	$file_name[] = $data_type;
	$file_name[] = $data_set_size;
	
	$file_name = strtolower(implode('-', $file_name)). '.ini';
	
	file_put_contents(CONFIG_DIR. '/'. $file_name, $config_file);
	
	return $file_name;
}
?>