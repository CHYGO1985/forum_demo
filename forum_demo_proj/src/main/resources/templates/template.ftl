<html lang="en">
<body>
    Hello freemarker!

    ${(value1) ! "default"}

    <#-- print out a list of items from a list without index -->
    The colors are :
    <#list colors as color>
        <p> ${color}
    </#list>

    <#-- print out a list of items from a list with index -->
    <#list 0..colors?size-1 as i>
        <p>${i} ${colors[i]}
    </#list>

    <#-- print out a list of items from hash -->
    <#list squares as num, res>
        <p> The num is : ${num} The result is : ${res}
    </#list>

    <#-- access customised java class -->
    <#-- access private field object.field -->
    <#-- access methods: object.methods() -->
        <p> The user name is: ${user.getName()}

    <#-- define an variable -->
    <#assign title = "JJ's forum">
    <p> The title is : ${title}

    <#-- include a file and parse it -->
    <#include "header.html" parse=true >

    <#-- define a function-->
    <#function addition x y>
        <#return x + y>
    </#function>

    <h2>${addition(5, 5)}</h2>

    <!-- date format -->
    <!-- ${(viewObj.question.createDate?string('yyyy-MM-dd HH:mm:ss')) ! "1900-01-01"} -->

</body>
</html>