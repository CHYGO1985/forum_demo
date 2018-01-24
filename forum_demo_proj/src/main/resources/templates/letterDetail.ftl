<#include "header.html" parse = true>
<link rel="stylesheet" media="all" href="../styles/letter.css">
<div id="main">
    <div class="zg-wrap zu-main clearfix ">
        <ul class="letter-chatlist">
            <#if convos?has_content>
            <#list convos as convo>
            <li id="msg-item-4009580">
                <a class="list-head">
                    <img alt="头像" src="${(convo.user.headUrl) !}">
                </a>
                <div class="tooltip fade right in">
                    <div class="tooltip-arrow"></div>
                    <div class="tooltip-inner letter-chat clearfix">
                        <div class="letter-info">
                            <p class="letter-time">${(convo.message.createDate?string('yyyy-MM-dd HH:mm:ss')) ! "1900-01-01"}</p>
                            <!-- <a href="javascript:void(0);" id="del-link" name="4009580">删除</a> -->
                        </div>
                        <p class="chat-content">
                            ${(convo.message.content) !}
                        </p>
                    </div>
                </div>
            </li>
            </#list>
            </#if>
        </ul>

    </div>
</div>
<#include "js.html" parse = true>
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.html" parse = true>