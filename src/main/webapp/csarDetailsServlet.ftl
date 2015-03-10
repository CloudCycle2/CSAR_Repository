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
            <li><a href="#upload" data-toggle="tab">Upload new Version</a></li>
            <li><a href="#importFromWinery" data-toggle="tab">New version from Winery</a></li>
            <li><a href="#rename" data-toggle="tab">Rename CSAR</a></li>
            <li><a href="#delete" data-toggle="tab">Delete CSAR</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
        	<div class="tab-pane fade" id="upload" style="padding-top: 20px;">
            	<form action="${basePath}/uploadcsarfile?csarId=${csar.id}" method="POST" enctype="multipart/form-data" class="form-horizontal">
					<div class="form-group">
						<label class="col-sm-2 control-label" for="csarFile">File</label>
				    	<div class="col-sm-10">
				    		<input type="file" id="csarFile" name="csarFile" class="form-control" />
						</div>
					</div>
					<div class="form-group">
					    <div class="text-left col-sm-12">
					      <button type="submit" class="btn btn-success pull-right">
					      	<span class="glyphicon glyphicon-plus"></span> 
					      	Upload new CSAR file</button>
						</div>
					</div>
				</form>
            </div>
            <div class="tab-pane fade" id="importFromWinery" style="padding-top: 20px;">
	            <#if wineryServers?size gt 0>
		            <form action="${basePath}/newversionfromwinery" method="POST" class="form-horizontal">
			            <div class="form-group">
				            <label for="wineryIdSelect" class="col-sm-2 control-label">Winery Server</label>
				            <div class="col-sm-10">
					            <select id="wineryIdSelect" class="form-control" name="wineryId">
					            	<#list wineryServers as ws>
					            		<option value="${ws.getId()}">${ws.getName()}</option>
					            	</#list>
					            </select>
				            </div>
			            </div>
			            <input type="hidden" name="servicetemplate" value="${serviceTemplate}" />
			            <input type="hidden" name="csarId" value="${csar.id}" />
			            <button type="submit" class="btn btn-primary pull-right">
							<span class="glyphicon glyphicon-import"></span> 
							Import
				</button>
			        </form>
			    <#else>
			    	<div class="alert alert-warning" role="alert">No Winery Servers found</div>
			    </#if>
			</div>
            <div class="tab-pane fade" id="rename" style="padding-top: 20px;">
                <form action="${basePath}/editcsarname" method="POST" class="form-horizontal">
                	<input type="hidden" name="csarId" value="${csar.id}"/>
					<div class="form-group">
						<label for="csarName" class="col-sm-2 control-label">CSAR Name</label>
				    	<div class="col-sm-10">
    						<input type="text" class="form-control" id="csarName" name="name" value="${csar.name}"/>
						</div>
					</div>
					<button type="submit" class="btn btn-success pull-right">
      					<span class="glyphicon glyphicon-edit"></span> 
      					Rename CSAR
      				</button>
				</form>
            </div>
            <div class="tab-pane fade" id="delete" style="padding-top: 20px;">
                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
	        		<span class="glyphicon glyphicon-remove"></span>
	        		Delete
    			</button>
            </div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>

<div class="row" style="margin-bottom: 20px;">
	<div class="col-lg-12">
		<h2>CSAR Details</h2>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Name:</div>
			<div class="col-md-9">${csar.name}</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Namespace:</div>
			<div class="col-md-9"><#if csar.namespace??>${csar.namespace}</#if></div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Service Template ID:</div>
			<div class="col-md-9"><#if csar.serviceTemplateId??>${csar.serviceTemplateId}</#if></div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Versions:</div>
			<div class="col-md-9">${csarFiles?size}</div>
		</div>
	</div>
</div>

<div class="row" style="margin-bottom: 20px;">
	<div class="col-lg-12">
		<h2>Versions</h2>
		<#if csarFiles?size gt 0>
			<table id="csarFileList" class="table table-striped table-bordered" border="1">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Version</th>
						<th>Upload date</th>
						<th>Size</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
			<#list csarFiles as csarFile>
				<tr>
					<td>${csarFile.id}</td> 
					<td>
					    <a href="${basePath}/csarfile/${csarFile.id}">${csarFile.name}</a>
					</td>
					<td>${csarFile.version}</td>
					<td>${csarFile.uploadDate}</td>
					<td>${StringUtils.readableFileSize(csarFile.hashedFile.size)}</td>
					<td style="text-align: center;">
						<a href="${basePath}/downloadcsarfile?csarfileid=${csarFile.id}"><span class="glyphicon glyphicon-download-alt"></span></a>
					</td>
				</tr>
			</#list>
			</tbody>
			</table>
		<#else>
			<div class="alert alert-warning" role="alert">No versions uploaded</div>
		</#if>
	</div>
</div>
<!-- ./row -->

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#csarFileList').dataTable();
		});
	}
</script>

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteModalLabel">Delete CSAR</h4>
            </div>
            <div class="modal-body">
                Do you really want to delete <strong>${csar.name}</strong>?
            </div>
            <div class="modal-footer">
                <a href="${basePath}/deletecsar/${csar.id}">
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
</@layout.sb_admin>