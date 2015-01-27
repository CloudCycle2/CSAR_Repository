<#import "layout.ftl" as layout>
<@layout.sb_admin>

<table id="userList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
		</tr>
	</thead>
	<tbody>
<#list users as user>
	<tr>
		<td>${user.id}</a></td>
		<td>${user.name}</a></td>
	</tr>
</#list>
</tbody>
</table>

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#userList').dataTable();
		});
	}
</script>

</@layout.sb_admin>