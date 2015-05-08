<#if errorMessages??>
    <div class="alert alert-warning" role="alert">${errorMessages}</div>
<#elseif deployedCsars?has_content>
<table id="ciList" class="table table-striped table-bordered" border="1">
    <thead>
        <tr>
            <th>Name</th>
            <th>Vinothek</th>
            <th>Build-Plans</th>
        </tr>
    </thead>
    <tbody>
        <#list deployedCsars as deployedCsar>
            <#assign buildPlans = PlanInvocationHelper.generateLinkToBuildPlan(openToscaServer, deployedCsar.title)> 
            <tr>
                <td><a href="${basePath}/csarfile/${deployedCsar.id}">${deployedCsar.title}</a></td>
                <td><a href="http://${otHost}:8080/vinothek/ApplicationElement.jsp?applicationId=http://${otHost}:1337/containerapi/CSARs/${deployedCsar.title}/Content/SELFSERVICE-Metadata/&container=${otHost}" target="_blank">Vinothek</a></td>
                <td style="text-align: center;">
                    <#if buildPlans?has_content>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            Plans <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <#list buildPlans as link>
                                <li>
                                    <a href="${link.href}" target="_blank">${link.text}</a>
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