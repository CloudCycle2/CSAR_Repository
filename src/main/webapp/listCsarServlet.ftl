<#import "layout.ftl" as layout>
<@layout.sb_admin>

<form action="${basePath}/createcsar" method="post">
	  <p>Name: <input name="csarName" type="text" size="30" maxlength="30"></p>
	<input type="submit" value="Create new Csar" />
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
		<td><a href="${basePath}/deletecsar/${csar.id}">Delete</a></td>
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