<?php
//вместо analyzer.jar путь к исполняемому java файлу
//$_POST['search']="help";
$data = file_get_contents("php://input");
$_POST = json_decode($data, TRUE);

if($_POST['search']!="")
{
    $query=$_POST['search'];
	$output=exec("java -jar analyzer.jar ".$query);
	$_POST['search']="";
	echo $output;
}
?>
