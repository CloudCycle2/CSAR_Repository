<#import "layout.ftl" as layout>
<@layout.sb_admin>

<form action="${basePath}/createcsar" method="POST" class="form-horizontal">
	<div class="form-group">
    	<label class="col-sm-2 control-label" for="serverName">Name</label>
    	<div class="col-sm-10">
    		<input type="text" class="form-control" id="csarName" name="csarName">
		</div>
	</div>
	<div class="form-group">
	    <div class="text-right col-sm-12">
	      <button type="submit" class="btn btn-success">
	      	<span class="glyphicon glyphicon-plus"></span> 
	      	&nbsp;Create new CSAR</button>
		</div>
	</div>
</form>

<table id="csarList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>CSAR Name</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
<#list csars as csar>
	<tr>
		<td><a href="${basePath}/csar/${csar.id}">${csar.name}</a></td>
		<td>
			<a href="${basePath}/deletecsar/${csar.id}" onclick="javascript: return confirm('Are you sure?');">
				<span class="glyphicon glyphicon-remove"></span>
			</a>
		</td>
	</tr>
</#list>
</tbody>
</table>

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#csarList').dataTable();
		});
	}
</script>

</@layout.sb_admin>