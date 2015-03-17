<#import "layout.ftl" as layout>
<@layout.sb_admin>

<div class="panel panel-default">
    <div class="panel-heading">
        Operations
    </div>
    <!-- /.panel-heading -->
    <div class="panel-body">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs">
            <li><a href="#create" data-toggle="tab">Create User</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
        	<div class="tab-pane fade" id="create" style="padding-top: 20px;">
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
				    <button type="submit" class="btn btn-success pull-right">
			            <span class="glyphicon glyphicon-plus"></span> 
			            Create new User
			        </button>
				</form>
            </div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>

<div class="row" style="margin-bottom: 20px;">
	<div class="col-lg-12">
		<h2>Created Users</h2>
		<#if users?size gt 0>
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
					<td style="text-align: center;">
					<button type="button" class="btn btn-xs btn-danger" data-toggle="modal" data-target="#deleteModal${user.id}">
			    		<span class="glyphicon glyphicon-remove"></span>
			    		Delete
					</button>
				</tr>
				<!-- Delete Modal -->
			<div class="modal fade" id="deleteModal${user.id}" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
			    <div class="modal-dialog">
			        <div class="modal-content">
			            <div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
			                            aria-hidden="true">&times;</span></button>
			                <h4 class="modal-title" id="deleteModalLabel">Delete user</h4>
			            </div>
			            <div class="modal-body">
			                Do you really want to delete the user <strong>${user.name}</strong>?
			            </div>
			            <div class="modal-footer">
			                <a href="${basePath}/deleteuser/${user.id}">
				                <button type="button" class="btn btn-danger pull-right" style="margin: 3px;">
				                	Delete
				                </button>
			                </a>
			                <button type="button" class="btn btn-default pull-right" data-dismiss="modal" style="margin: 3px;">Cancel</button>
			                <br />
			                <br />
			            </div>
			        </div>
			    </div>
			</div>
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
		<#else>
			<div class="alert alert-warning" role="alert">No users created</div>
		</#if>
	</div>
</div>

</@layout.sb_admin>