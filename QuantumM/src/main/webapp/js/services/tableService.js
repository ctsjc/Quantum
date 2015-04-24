function cascadeTable(familyArray,parentId,childArray){
		if(Object.keys(familyArray).length === 0 ){// If family is empty... then add childArray as a family
	    	familyArray=childArray;
	    }else{
				// search srcTdId in the sbsb, and replace it with sxb
	    	var updatedParentId=parseInt(parentId)+1;
    	    for( v in familyArray){
    	    	//there is a issue in this code, if v is itself a table, then we can not search into it.
    	    	if(Object.prototype.toString.call( familyArray[v] ) === '[object Object]' ){
    	    		cascadeTable( familyArray[v],parentId,childArray);
    	    	}
    	    	if(familyArray[v] === updatedParentId){
    	    		familyArray[v] = childArray;
    	    	}
    	    }
	    }
		return familyArray;
	}

function appendTable(familyArray,parentId,childArray){
	for( v in familyArray){
    	//there is a issue in this code, if v is itself a table, then we can not search into it.
    	if(Object.prototype.toString.call( familyArray[v] ) === '[object Object]' ){
    		appendTable( familyArray[v],parentId,childArray);
    	}
    	if(familyArray.table === parseInt(parentId) ){
    		
    		// apend to familyArray with childArray
    		
    		var row_name = 'r1_'+childArray.a.row;
    		familyArray[row_name]=childArray.a;
    		
    		row_name = 'r2_'+childArray.b.row;
    		familyArray[row_name]=childArray.b;
    	}
    }
	return familyArray;
}//end of appendTable

function removeFromTable(familyArray,parentId){
	for( v in familyArray){
    	//there is a issue in this code, if v is itself a table, then we can not search into it.
    	if(Object.prototype.toString.call( familyArray[v] ) === '[object Object]' ){
    		appendTable( familyArray[v],parentId,childArray);
    	}
    	if(familyArray.table === parseInt(parentId) ){
    		
    	}
    }
	return familyArray;
}//end of appendTable

function readTable(familyArray){
    for( v in familyArray){
    	//there is a issue in this code, if v is itself a table, then we can not search into it.
    	if(Object.prototype.toString.call( familyArray[v] ) === '[object Object]' ){
    		readTable( familyArray[v]);
    	}
    	var id = angular.element(familyArray[v]);
    	var ele = document.getElementById(id[0]);
    	if ((ele !== undefined) && (ele !== null) && (ele.value !== undefined)) {
    		familyArray[v]=ele.value;
    	}
    }//end of for
}//end of readTable