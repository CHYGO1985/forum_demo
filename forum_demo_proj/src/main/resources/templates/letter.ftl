<#include "header.html" parse = true>
<link rel="stylesheet" media="all" href="../styles/letter.css">
    <div id="main">
        <div class="zg-wrap zu-main clearfix ">
            <ul class="letter-list">
                <#if convoInfor?has_content>
                <#list convoInfor as convo>
                <li id="conversation-item-10005_622873">
                    <a class="letter-link" href="/msg/detail?convoId=${convo.message.convoId}"></a>
                    <div class="letter-info">
                        <span class="l-time">${(convo.message.createDate?string('yyyy-MM-dd HH:mm:ss')) ! "1900-01-01"}</span>
                        <div class="l-operate-bar">
                            <!--<a href="javascript:void(0);" class="sns-action-del" data-id="10005_622873">
                            删除
                            </a>-->
                            <a href="/msg/detail?convoId=${convo.message.convoId}">
                                共${(convo.message.id) ! "0"}条会话
                            </a>
                        </div>
                    </div>
                    <div class="chat-headbox">o
                        <span class="msg-num">
                            ${(convo.unread) !}
                        </span>
                        <a class="list-head">
                            <img alt="头像" src="${convo.user.headUrl}">
                        </a>
                    </div>
                    <div class="letter-detail">
                        <a title="通知" class="letter-name level-color-1">
                            ${(convo.user.name) !}
                        </a>
                        <p class="letter-brief">
                            ${(convo.message.content) !}
                        </p>
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