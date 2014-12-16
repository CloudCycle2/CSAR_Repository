<#import "layout.ftl" as layout>
<@layout.sb_admin>

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

<script>
${r"$(document).ready(function() {
    $('#example').dataTable();
} );"}
</script>

<link rel="stylesheet" href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.min.css">
<script src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>

</@layout.sb_admin>