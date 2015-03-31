<#if errorMessages??>
    <div class="alert alert-warning" role="alert">${errorMessages}</div>
<#elseif deployedCsars?has_content>
<table id="ciList" class="table table-striped table-bordered" border="1">
    <thead>
        <tr>
            <th>Name</th>
            <th>Build-Plans</th>
        </tr>
    </thead>
    <tbody>
        <#list deployedCsars as deployedCsar>
            <#assign buildPlans = PlanInvocationHelper.generateLinkToBuildPlan(openToscaServer, deployedCsar.title)> 
            <tr>
                <td><a href="${basePath}/csarfile/${PlanInvocationHelper.getCsarFileId(openToscaServer, deployedCsar.title)}">${deployedCsar.title}</a></td>
                <td style="text-align: center;">
                    <#if buildPlans?has_content>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            Plans <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <#list buildPlans as link>
                                <li>
                                    <a href="${link.href}">${link.text}</a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <#else>
                        Nothing found
                    </#if>
                </td>
            </tr>
        </#list>
     </tbody>
</table>
<#else>
   <div class="alert alert-warning" role="alert">No deployed CSARs found</div>
</#if>