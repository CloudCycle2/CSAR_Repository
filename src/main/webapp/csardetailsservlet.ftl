<#import "layout.ftl" as layout>
<@layout.sb_admin>

<div class="row">
    <div class="col-lg-12">

<form action="${basePath}/uploadcsarfile?csarId=${csar.id}" method="POST" enctype="multipart/form-data" class="form-horizontal">
	<div class="form-group">
    	<div class="col-sm-12">
    		<input type="file" id="csarFile" name="csarFile" placeholder="File">
		</div>
	</div>
	<div class="form-group">
	    <div class="text-right col-sm-12">
	      <button type="submit" class="btn btn-success">
	      	<span class="glyphicon glyphicon-plus"></span> 
	      	&nbsp;Upload new CSAR file</button>
		</div>
	</div>
</form>

<table id="csarFileList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Version</th>
			<th>upload date</th>
			<th>Size</th>
			<th>fileName</th>
			<th>hash</th>
			<th>Details</th>
			<th>download</th>
			<th>delete</th>
		</tr>
	</thead>
	<tbody>
<#list csarFiles as csarFile>
	<tr>
		<td>${csarFile.id}:</td> 
		<td>${csarFile.name}</td>
		<td>${csarFile.version}</td>
		<td>${csarFile.uploadDate}</td>
		<td>${csarFile.hashedFile.size}</td>
		<td>${csarFile.hashedFile.filename}</td>
		<td>${csarFile.hashedFile.hash}</td>
		<td><a href="${basePath}/csarfile/${csarFile.id}">Details</a>
		<td><a href="${basePath}/downloadcsarfile?csarfileid=${csarFile.id}">Download</a></td>
		<td><a href="${basePath}/deletecsarfile?csarfileid=${csarFile.id}">Delete</a></td>
	</tr>
</#list>
</tbody>
</table>
    </div>
    <!-- /.col-lg-12 -->
</div>

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#csarFileList').dataTable();
		});
	}
</script>

</@layout.sb_admin>