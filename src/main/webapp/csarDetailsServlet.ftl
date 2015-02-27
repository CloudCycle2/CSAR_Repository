<#import "layout.ftl" as layout>
<@layout.sb_admin>

<div class="row">
    <div class="col-lg-12">

<div class="panel panel-default">
    <div class="panel-heading">
        Operations
    </div>
    <!-- /.panel-heading -->
    <div class="panel-body">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs">
            <li class="active"><a href="#upload" data-toggle="tab">Upload</a></li>
            <li><a href="#importFromWinery" data-toggle="tab">Import from winery</a></li>
            <li><a href="#rename" data-toggle="tab">Rename</a></li>
            <li><a href="#delete" data-toggle="tab">Delete</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
        	<div class="tab-pane fade in active" id="upload" style="padding-top: 20px;">
            	<form action="${basePath}/uploadcsarfile?csarId=${csar.id}" method="POST" enctype="multipart/form-data" class="form-horizontal">
					<div class="form-group">
				    	<div class="col-sm-12">
				    		<input type="file" id="csarFile" name="csarFile" placeholder="File"/>
						</div>
					</div>
					<div class="form-group">
					    <div class="text-left col-sm-12">
					      <button type="submit" class="btn btn-success">
					      	<span class="glyphicon glyphicon-plus"></span> 
					      	&nbsp;Upload new CSAR file</button>
						</div>
					</div>
				</form>
            </div>
            <div class="tab-pane fade in" id="importFromWinery" style="padding-top: 20px;">
	            <h3>Import new version from winery</h3>
				<#if csar.namespace?? && csar.serviceTemplateId??>
					<select class="form-control">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
				<#else>
					<div class="alert alert-warning" role="alert">Servicetemplate and Namespace not defined. Pulling a new version from winery is not possible.</div>
				</#if>
			</div>
            <div class="tab-pane fade" id="rename" style="padding-top: 20px;">
                <form action="${basePath}/editcsarname" method="POST" class="form-horizontal">
                	<input type="hidden" name="csarId" value="${csar.id}"/>
					<div class="form-group">
				    	<div class="col-sm-12">
    						<input type="text" name="name" placeholder="${csar.name}"/>
						</div>
					</div>
					<div class="form-group">
	    				<div class="text-left col-sm-12">
	      					<button type="submit" class="btn btn-success">
	      					<span class="glyphicon glyphicon-edit"></span> 
	      					&nbsp;Rename CSAR</button>
						</div>
					</div>
				</form>
            </div>
            <div class="tab-pane fade" id="delete" style="padding-top: 20px;">
                <form action="${basePath}/deletecsar/${csar.id}" method="GET" class="form-horizontal">
					<div class="form-group">
	    				<div class="text-left col-sm-12">
	      					<button type="submit" class="btn btn-danger" onclick="javascript: return confirm('Are you sure?');">
	      					<span class="glyphicon glyphicon-remove"></span> 
	      					&nbsp;Delete CSAR</button>
						</div>
					</div>
				</form>
            </div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>

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
		<td>${csarFile.name}</td>
		<td>${csarFile.version}</td>
		<td>${csarFile.uploadDate}</td>
		<td>${StringUtils.readableFileSize(csarFile.hashedFile.size)}</td>
		<td style="text-align: center;">
			<a href="${basePath}/csarfile/${csarFile.id}"><span class="glyphicon glyphicon-file"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="${basePath}/downloadcsarfile?csarfileid=${csarFile.id}"><span class="glyphicon glyphicon-download-alt"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="${basePath}/deletecsarfile?csarfileid=${csarFile.id}" onclick="javascript: return confirm('Are you sure?');"><span class="glyphicon glyphicon-remove"></span></a>
		</td>
		
	</tr>
</#list>
</tbody>
</table>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- ./row -->

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#csarFileList').dataTable();
		});
	}
</script>

</@layout.sb_admin>