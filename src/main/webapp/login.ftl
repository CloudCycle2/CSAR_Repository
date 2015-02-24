<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>csarrepo</title>
    <!-- Bootstrap Core CSS -->
    <link href="${basePath}/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="${basePath}/static/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
    <!-- DataTables CSS -->
    <link href="${basePath}/static/css/plugins/dataTables.bootstrap.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${basePath}/static/css/sb-admin-2.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${basePath}/static/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.static/js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <#if errors?has_content>
        <div class="alert alert-danger alert-dismissable" style="position: absolute;top: 5px;left:50%;width:500px;margin-left:-250px">
          <button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>
         <ul>
           <#list errors as error>
             <li>${error}</li>
           </#list>
         </ul>
       </div>
    </#if>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form action="${basePath}/login" method="post" role="form">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="username" value="admin" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="admin">
                                </div>
                                <button class="btn btn-lg btn-primary btn-block">Login</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- jQuery -->
    <script src="${basePath}/static/js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="${basePath}/static/js/bootstrap.min.js"></script>
    <!-- Metis Menu Plugin JavaScript -->
    <script src="${basePath}/static/js/plugins/metisMenu/metisMenu.min.js"></script>
    <!-- DataTables JavaScript -->
    <script src="${basePath}/static/js/plugins/dataTables/jquery.dataTables.js"></script>
    <script src="${basePath}/static/js/plugins/dataTables/dataTables.bootstrap.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="${basePath}/static/js/sb-admin-2.js"></script>
</body>
</html>