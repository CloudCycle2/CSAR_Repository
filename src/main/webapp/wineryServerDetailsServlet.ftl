<#import "layout.ftl" as layout>
<@layout.sb_admin>

<a href="${wineryServer.address}" target="_blank" class="btn btn-block btn-lg btn-primary">
	<span class="glyphicon glyphicon-share-alt"></span>
	Visit winery 
</a>

<h3>Edit winery server</h3>
<form action="${basePath}/updatewineryserver/${wineryServer.id}" method="POST" class="form-horizontal">
  <div class="form-group">
    <label for="inputWineryServerName" class="col-sm-1 control-label">Name</label>
    <div class="col-sm-11">
      <input name="wineryServerName" type="text" class="form-control" id="inputWineryServerName" value="${wineryServer.name}">
    </div>
  </div>
  <div class="form-group">
    <label for="inputWineryServerUrl" class="col-sm-1 control-label">URL</label>
    <div class="col-sm-11">
      <input name="wineryServerUrl" type="text" class="form-control" id="inputWineryServerUrl" value="${wineryServer.address}">
    </div>
  </div>
  <div class="form-group">
    <div class="text-right col-sm-12">
    	<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
        	<span class="glyphicon glyphicon-remove"></span>
        	Delete
    	</button>
      	<button type="submit" class="btn btn-success">
      		<span class="glyphicon glyphicon-ok"></span>
      		Update winery server
      	</button>
    </div>
  </div>
</form>

<h3>Available ServiceTemplates</h3>
<hr />
<#list servicetemplates as st>
	<div class="pull-left">
		<strong>${st.name}</strong> <br />
		<strong>Id:</strong> ${st.id} <br />
		<strong>Namespace:</strong> ${st.namespace}
	</div>
	<div class="pull-right">
		<a href="#" class="btn btn-default"><span class="glyphicon glyphicon-import"></span> Import as new CSAR</a>
	</div>
	<div class="clearfix"></div>
	<hr />
</#list>
<h3>Winery users</h3>

<a href="${wineryServer.address}" target="_blank" class="btn btn-block btn-lg btn-primary">
	<span class="glyphicon glyphicon-share-alt"></span>
	Visit winery 
</a>

 <!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Delete Winery server</h4>
            </div>
            <div class="modal-body">
                Do you really want to delete <strong>${wineryServer.name}</strong>?
            </div>
            <div class="modal-footer">
                <a href="${basePath}/deletewineryserver/${wineryServer.id}">
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