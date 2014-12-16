<#import "layout.ftl" as layout>
<@layout.sb_admin>

<table id="csarList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>CSAR Name</th>
			<th>-</th>
		</tr>
	</thead>
	<tbody>
<#list csars as csar>
	<tr>
		<td>${csar.name}</td>
		<td><a href="${basePath}/csar/${csar.id}">Details</a></td>
	</tr>
</#list>
</tbody>
</table>

<script>
${r"$(document).ready(function() {
    $('#csarList').dataTable();
} );"}
</script>

</@layout.sb_admin>