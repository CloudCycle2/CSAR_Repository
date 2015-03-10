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
        	<li><a href="#create" data-toggle="tab">Create CSAR</a></li>
            <li><a href="#import" data-toggle="tab">Import from Winery</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div class="tab-pane fade" id="create" style="padding-top: 20px;">
      			<form action="${basePath}/createcsar" method="POST" class="form-horizontal">
					<div class="form-group">
				    	<label class="col-sm-2 control-label" for="serverName">Name</label>
				    	<div class="col-sm-10">
				    		<input type="text" class="form-control" id="csarName" name="csarName">
						</div>
					</div>
					<div class="form-group">
					    <div class="text-left col-sm-12">
					      <button type="submit" class="btn btn-success pull-right">
					      	<span class="glyphicon glyphicon-plus"></span> 
					      	&nbsp;Create new CSAR</button>
						</div>
					</div>
				</form>          
            </div>
            
            <div class="tab-pane fade" id="import" style="padding-top: 20px;">
            	<#if wineryList?size gt 0>
	            	<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
							Select Winery Server <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu" style="z-index: 1000">
							<#list wineryList as winery>
								<li>
									<a href="${basePath}/wineryserver/${winery.id}#serviceTemplates">${winery.name}</a>
								</li>
							</#list>
						</ul>
					</div>
				<#else>
					<div class="alert alert-warning" role="alert">No Winery Servers created</div>
				</#if>
            </div>
            
		</div>
	</div>
</div>


<table id="csarList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Count</th>
			<th>Last Version</th>
		</tr>
	</thead>
	<tbody>
<#list csars as csar>
	<tr>
		<td style="text-align: center">${csar.id}</td>
		<td><a href="${basePath}/csar/${csar.id}">${csar.name}</a></td>
		<td style="text-align: center;">${csar.csarFiles?size}</td>
		<td style="text-align: center;">
			<#if csar.csarFiles?has_content>
				${csar.csarFiles?sort_by("version")?last.version}
			<#else>
				-
			</#if>
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