<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:shiro="http://www.pollix.at/thymeleaf/shiro">
<header th:replace="inc/header::headerFragment('${tableRemark}')"/>
<body>
<div id="main">
    <div id="toolbar">
        <a shiro:hasPermission="upms:${simpleEntityName}:create" class="waves-effect waves-button" href="javascript:;"
           onclick="crud.createAction()"><i class="zmdi zmdi-plus"></i> 新增${tableRemark}</a>
        <a shiro:hasPermission="upms:${simpleEntityName}:update" class="waves-effect waves-button" href="javascript:;"
           onclick="crud.updateAction()"><i class="zmdi zmdi-edit"></i> 编辑${tableRemark}</a>
        <a shiro:hasPermission="upms:${simpleEntityName}:delete" class="waves-effect waves-button" href="javascript:;"
           onclick="crud.deleteAction()"><i class="zmdi zmdi-close"></i> 删除${tableRemark}</a>
    </div>
    <table id="table"></table>
</div>
<th:block th:replace="inc/footer::footerFragment"/>
<script type="text/javascript">
    var crud = new Crud({
        idField: '${idField}',
        listUri: '<th:block th:text="@{/manage/${simpleEntityName}/list/}"/>',
        createUri: '<th:block th:text="@{/manage/${simpleEntityName}/create}"/>',
        updatePath: '<th:block th:text="@{/manage/${simpleEntityName}/update/}"/>',
        deletePath: '<th:block th:text="@{/manage/${simpleEntityName}/delete/}"/>',
        entityName: '${tableRemark}',
        columns: [
            {field: 'ck', checkbox: true}
        <#list columns as col>
            , {field: '${col.name}', title: '${col.remark}' <#if col.pk>, sortable: true, align: 'center'</#if>}
        </#list>
        ]
    });
</script>
</body>
</html>