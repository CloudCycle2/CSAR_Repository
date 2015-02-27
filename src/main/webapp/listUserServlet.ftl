<#import "layout.ftl" as layout>
<@layout.sb_admin>

<form action="${basePath}/createuser" method="POST" class="form-horizontal">
    <div class="form-group">
        <label class="col-sm-2 control-label" for="serverName">Username</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="name" name="name">
        </div>
     </div>
     <div class="form-group">
        <label class="col-sm-2 control-label" for="serverName">Password</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="password" name="password">
        </div>
     </div>
     <div class="form-group">
        <label class="col-sm-2 control-label" for="serverName">Mail</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="mail" name="mail">
        </div>
    </div>
    <div class="form-group">
        <div class="text-left col-sm-12">
          <button type="submit" class="btn btn-success">
            <span class="glyphicon glyphicon-plus"></span> 
            &nbsp;Create new User</button>
        </div>
    </div>
</form>

<table id="userList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
<#list users as user>
	<tr>
		<td>${user.id}</a></td>
		<td>${user.name}</a></td>
		<td style="text-align: center;"><a href="${basePath}/deleteuser/${user.id}" onclick="javascript: return confirm('Are you sure?');"><span class="glyphicon glyphicon-remove"></span></a></td>
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