[{
    "template": "template.pptx",
    "data": [{
        "name": "Chart5",
        "table":
            [

                [null, <#list headers as header>{"string": "${header}"}<#sep>,</#list>],
                [],
<#list records as record>                [<#list record as recordValue>{"<#if recordValue?is_first>string<#else>number</#if>": <#if recordValue?is_first>"${recordValue}"<#else>${recordValue}</#if>}<#sep>, </#list>]<#sep>,
</#list>

            ]
        }
    ]
}]
