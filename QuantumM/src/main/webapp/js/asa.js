var app = angular.module('testApp');
// not working
app.run(function($window) {
    console.log("app run");
    var windowElement = angular.element($window);
    windowElement.on('beforeunload', function (event) {
        // do whatever you want in here before the page unloads.        

        // the following line of code will prevent reload or navigating away.
        event.preventDefault();
    });
});
app.controller('ListController', ['$scope','$compile','$element',
                                  function ($scope,$compile,$element) {
	
	$scope.familyArray=new Object();
	$scope.idid = 1000;
	
	/*function cascadeTable(familyArray,parentId,childArray){
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
	}*/
	
	/*function appendTable(familyArray,parentId,childArray){
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
*/		
	$scope.menuOptions = [
	                      ['ADD TABLE', function ($itemScope,$srcTdId) {
	                  		console.log('---ADD--- id :: '+$srcTdId+' ---');
	                  		var tid=$scope.idid++;
	                  		
	                  		var tr1=$scope.idid++;
	                  		var td11=$scope.idid++;
	                  		var tx1=$scope.idid++;
	                  		
	                  		var tr2=$scope.idid++;
	                  		var td21=$scope.idid++;
	                  		var tx21=$scope.idid++;
	                  		var td22=$scope.idid++;
	                  		var tx22=$scope.idid++;
	                  		
	                  		var el = $compile( "" +
	                  				"<table style=\"width: 97%;margin-right: 1.5%; margin-left: 1.5%; margin-top: 5px; margin-bottom: 5px;table-layout: fixed;\" border=1 id= "+(tid)+">" +
		                  				"<tr  bgcolor=#FFFF99 id="+(tr1)+">" +
		                  					"<td colspan=2 id="+(td11)+">" +
		                  						"<textarea id= "+(tx1)+" style=background-color:#FFFF99;width:100%; rows=2 > </textarea>" +
		                  					"</td>" +
		                  				"</tr>" +
		                  				
		                  				"<tr id= "+(tr2)+">" +
		                  					"<td id= "+(td21)+">" +
		                  							"<textarea id= "+(tx21)+" style=width:100%;height: 100%; rows=2 > </textarea>" +
		                  					"</td>" +
		                  					"<td id= "+(td22)+">" +
		                  						"<textarea id= "+(tx22)+" style=background-color:#E6E6FA;width:100%; rows=2 > </textarea>" +
		                  					"</td>" +
		                  				"</tr>" +
	                  				"</table>" )( $scope );                 				
	                  		
	                    	  var result = document.getElementById($srcTdId);
	                    	  angular.element(result).empty().append( el );
	                    	  
	                    	  console.log('TABLE :: '+tid);
	                    	    var sxb=new Object();
	                    	    sxb.table=tid;
	                    	    sxb.a ={row:tr1, val:tx1};;
	                    	    sxb.b={row:tr2, val:tx21};
	                    	    sxb.c={row:tr2, val:tx22};;
	                    	    $scope.familyArray=cascadeTable($scope.familyArray,$srcTdId, sxb);
	                    	                      				
                  				console.log('SBSB :: '+JSON.stringify($scope.familyArray));

	                      }],
	                      ['ADD ROW', function ($itemScope,$srcTdId,$srcTrId,$srcTblId) {
	                    	    var tr1=($scope.idid++);
	                    	    var td1 =($scope.idid++);
	                    	    var tx1 = ($scope.idid++);
	                    	    var td2 =($scope.idid++);
	                    	    var tx2 = ($scope.idid++);
		                  		console.log('---ADD ROW--- id :: '+$srcTdId+' ---'+$srcTblId);
		                    	  var el = $compile( 
		                    			  "<tr id= "+tr1+">" +
		                    			  		"<td id="+td1+">" +
		                  							"<textarea id= "+tx1+" style=width:100%; rows=2 > </textarea>" +
		                    			  		"</td>" +
		                    			  		"<td id="+td2+">" +
		                  							"<textarea id= "+tx2+" style=background-color:#E6E6FA;width:100%; rows=2 > </textarea>" +
		                    			  		"</td>" +
		                    			  "</tr>" )( $scope );
		                    	  
		                    	  var result = document.getElementById($srcTblId);
		                    	  angular.element(result).append( el );
		                    	  
		                    	  var sxb=new Object();		
		                    	  	sxb.row=tr1;
		                    	  	sxb.a={row:tr1, val:tx1};
		                    	  	sxb.b={row:tr1, val:tx2};
		                    	    $scope.familyArray=appendTable($scope.familyArray,$srcTblId,sxb);
		                    	    
	                  				console.log(($scope.familyArray));
  
		                      }],
		                      
	                      ['REMOVE ROW', function ($itemScope,$srcTdId,$srcTrId) {
		                  		console.log('---REMOVE	 ROW--- id :: '+$srcTdId+' ---'+$srcTrId);

	                    	  var result = document.getElementById($srcTrId);
	                    	  angular.element(result).remove();
	                      }],
		                      
	                      ['REMOVE TABLE', function ($itemScope,$srcTdId,$srcTrId,$srcTblId) {
	                    	  /*var el = $compile( 
	                    			  	"<tr  bgcolor=#FFFF99 id="+($scope.idid++)+">" +
	                  						"<td colspan=2 id="+($scope.idid++)+">" +
	                  							"<textarea id= "+($scope.idid++)+" style=background-color:#FFFF99;width:100%; rows=2 > </textarea>" +
	                  						"</td>" +
	                  					"</tr>"  )( $scope );
	                    	  
	                    	  var result = document.getElementById($srcTblId);
	                    	  angular.element(result).empty().append( el );*/
	                      }]
	                      ];//end of menuOption
	$scope.readAll=function(){
		$scope.clnfamilyArray={};
		angular.copy($scope.familyArray, $scope.clnfamilyArray);
		readTable($scope.clnfamilyArray);
		/*function readTable(familyArray){
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
*/	    console.log($scope.clnfamilyArray);
	}////end of readAll
}//end of controller
]);



app.directive('sB', function() {
	return {
		restrict: 'E',
		scope: {
			customerInfo: '=info'
		},
		templateUrl: 'sb.html'
	};
})
