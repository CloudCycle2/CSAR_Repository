<#import "layout.ftl" as layout>
<@layout.sb_admin>

<div class="row" style="margin-bottom: 20px;">
    <div class="col-lg-12">
		<h2>CSAR Information</h2>
		<!-- Csars -->
		<div class="col-lg-3 col-md-6">
        	<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-file-archive-o fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${csars}</div>
                            <div>CSARs created</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/csarlist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage CSARs</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
        <!-- CsarFiles -->
        <div class="col-lg-3 col-md-6">
        	<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-files-o fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${csarFiles}</div>
                            <div>Versions managed</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/csarlist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage CSARs</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
        <!-- HashedFiles -->
       <div class="col-lg-3 col-md-6">
        	<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-database fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${hashedFiles}</div>
                            <div>CSAR Files stored</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/csarlist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage CSARs</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
        <!-- plans -->
        <div class="col-lg-3 col-md-6">
        	<div class="panel panel-primary">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-tasks fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${csarPlans}</div>
                            <div>Plans invokeable</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/csarlist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage CSARs</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
	</div>
</div>

<div class="row" style="margin-bottom: 20px;">
    <div class="col-lg-12">
		<h2>OpenTosca Server Information</h2>
		<!-- otservers -->
        <div class="col-lg-3 col-md-6">
        	<div class="panel panel-success">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-cloud fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${otServers}</div>
                            <div>OpenTOSCA Servers</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/opentoscaserverlist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage OpenTOSCA Servers</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
        <!-- deployedCsars -->
        <div class="col-lg-3 col-md-6">
        	<div class="panel panel-success">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-briefcase fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${deployedCsars}</div>
                            <div>Deployed CSARs</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/opentoscaserverlist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage OpenTOSCA Servers</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
	</div>
</div>

<div class="row" style="margin-bottom: 20px;">
    <div class="col-lg-12">
		<h2>Winery Server Information</h2>
		<!-- wineryServers -->
        <div class="col-lg-3 col-md-6">
        	<div class="panel panel-warning">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-puzzle-piece fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${wServers}</div>
                            <div>Winery Servers</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/winerylist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage Winery Servers</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
	</div>
</div>


<div class="row" style="margin-bottom: 20px;">
    <div class="col-lg-12">
		<h2>User Information</h2>
		<!-- users -->
        <div class="col-lg-3 col-md-6">
        	<div class="panel panel-danger">
            	<div class="panel-heading">
                	<div class="row">
                    	<div class="col-xs-3">
                        	<i class="fa fa-users fa-5x"></i>
                        </div>
                        <div class="col-xs-9 text-right">
                        	<div class="huge">${users}</div>
                            <div>Users</div>
                        </div>
                    </div>
                </div>
                <a href="${basePath}/userlist">
                	<div class="panel-footer">
                    	<span class="pull-left">Manage Users</span>
                        <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                        <div class="clearfix"></div>
                    </div>
                </a>
            </div>
        </div>
	</div>
</div>

</@layout.sb_admin>