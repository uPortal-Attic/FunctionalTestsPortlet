<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%@ include file="/WEB-INF/jsp/testSelectorHeader.jsp" %>

<c:url var="mootoolsUrl" value="/js/mootools-release-1.11.js" />
<script type="text/javascript" src="${mootoolsUrl}"> </script>

<portlet:renderURL var="refreshUrl" />
<script type="text/javascript">
    function addPref(prefCount) {
        var prefsUL = $('prefs');
        var prefCount = prefsUL.getChildren().length / 2;
        
        //Setup the Name/Add Value LI
        var nameLI = new Element('li');
        nameLI.injectInside(prefsUL);
        
        var nameINPUT = new Element('input', { 
            'type' : 'text', 
            'name' : 'preferences[' + prefCount + '].name' 
        }); 
        nameINPUT.injectInside(nameLI);
        
        nameLI.appendText(" ");
        
        var addValueA = new Element('a', { 
            'href' : 'javascript:void(0)',
            'onclick' : 'addValue(' + prefCount + '); return false;'
        });
        addValueA.setText("Add Value");
        addValueA.injectInside(nameLI);
        
        nameLI.appendText(" - ");
        
        var deleteValueA = new Element('a', { 
            'href' : '${refreshUrl}'
        });
        deleteValueA.setText("Delete");
        deleteValueA.injectInside(nameLI);
        

        //Setup the values LI
        var valuesLI = new Element('li');
        valuesLI.injectInside(prefsUL);
        
        var valuesUL = new Element('ul', { 
            'id' : 'pref_vals_' + prefCount, 
            'style' : 'list-style: none; padding-top: 0px; padding-bottom: 10px;'
        }); 
        valuesUL.injectInside(valuesLI);
        
        return false;
    }
    
    function addValue(prefIndex) {
        var valuesUL = $('pref_vals_' + prefIndex);
        var valueCount = valuesUL.getChildren().length;

        var valueLI = new Element('li', { 
            'id' : 'pref_val_' + prefIndex + '_' + valueCount 
        });
        valueLI.injectInside(valuesUL);
        
        var valueINPUT = new Element('input', { 
            'type' : 'text', 
            'name' : 'preferences[' + prefIndex + '].values[' + valueCount + ']' 
        }); 
        valueINPUT.injectInside(valueLI);
        
        valueLI.appendText(" - ");
        
        var deleteValueA = new Element('a', { 
            'href' : '${refreshUrl}'
        });
        deleteValueA.setText("Delete");
        deleteValueA.injectInside(valueLI);
        
        return false;
    }
</script>

<portlet:actionURL var="formSubmitUrl" />
<form:form action="${formSubmitUrl}">
    <ul id="prefs" style="list-style: none;">
        <c:forEach var="preference" varStatus="prefStatus" items="${command.preferences}">
            <li>
                <form:input path="preferences[${prefStatus.index}].name" />
                <a href="javascript:void(0)" onclick="addValue(${prefStatus.index}); return false;">Add Value</a> -
                <portlet:actionURL var="deletePref">
                    <portlet:param name="delPrefIndex" value="${prefStatus.index}"/>
                </portlet:actionURL>
                <a href="${deletePref}">Delete</a>
            </li>
            <li>
                <ul id="pref_vals_${prefStatus.index}" style="list-style: none; padding-top: 0px; padding-bottom: 10px;">
                    <c:forEach var="value" varStatus="valueStatus" items="${preference.values}">
                        <li id="pref_val_${prefStatus.index}_${valueStatus.index}">
                            <form:input path="preferences[${prefStatus.index}].values[${valueStatus.index}]" />
                            <portlet:actionURL var="deleteValue">
                                <portlet:param name="delPrefIndex" value="${prefStatus.index}"/>
                                <portlet:param name="delValueIndex" value="${valueStatus.index}"/>
                            </portlet:actionURL>
                            <a href="${deleteValue}">Delete</a>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>
    <a href="javascript:void(0)" onclick="addPref(); return false;">Add Preference</a>

    <input type="submit" name="save" value="Update" />
</form:form>