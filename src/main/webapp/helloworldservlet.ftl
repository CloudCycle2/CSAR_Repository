<html>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>


<script>
${r"$(document).ready(function() {
    $('#example').dataTable();
} );"}
</script>
<body>

<h1>${csar.id}: ${csar.name}</h1>

<h2>CsarFile-Details</h2>

<table id="example" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>CsarFile-ID</th>
			<th>CsarFile-Name</th>
			<th>Version</th>
			<th>Size</th>
			<th>upload Date</th>
			<th>csarFile.fileId</th>
			<th>csarFile.hashedFile.fileName</th>
			<th>csarFile.hashedFile.hash</th>
		</tr>
	</thead>
	<tbody>
<#list csarFiles as csarFile>
	<tr>
		<td>${csarFile.id}:</td> 
		<td>${csarFile.name}</td>
		<td>${csarFile.version}</td>
		<td>${csarFile.size}</td>
		<td>${csarFile.uploadDate}</td>
		<td>${csarFile.fileId}</td>
		<td>${csarFile.hashedFile.fileName}</td>
		<td>${csarFile.hashedFile.hash}</td>
	</tr>
</#list>
</tbody>
</table>


</body>
</html>