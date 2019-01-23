'use strict';


App.controller('HomeController', ['$scope', 'HomeService','uiGridConstants',"$mdDialog", function($scope, HomeService,uiGridConstants,$mdDialog){
 
$scope.oneAtATime = true;

$scope.password = null;
$scope.passwordConfirmation = null;



$scope.deviceGrid = { 
    data:'deviceDetails',
    enableRowSelection: true, 
    enableRowHeaderSelection: false, 
    enableSelectAll: false,
    multiSelect: false,
    noUnselect: true,
    minRowsToShow :10,
    

    onRegisterApi: function (gridApi) { 
     gridApi.selection.on.rowSelectionChanged($scope, function (row) {
        var msg = 'row selected ' + row.isSelected;
       console.log(row.entity.devHardwareId);
       $scope.devHardwareId= row.entity.devHardwareId;
       HomeService.getPeripheralList(row.entity.devHardwareId).then (function success(response){
        $scope.peripheralDetails = response.data;
        $scope.errorMessage = '';
    },
    function error(response) {
        $mdDialog.show(
            $mdDialog.alert()
              .parent(angular.element(document.querySelector('#popupContainer')))
              .clickOutsideToClose(true)
              .title('Error occured!!!!!!')
              .textContent(response.data.message)
              .ariaLabel('')
              .ok('Ok')
        );
        
    });
        });
    }};



    $scope.deviceGrid.columnDefs = [
                     { field: 'deviceName', displayName: 'Device Name' },
                     { field: 'devHardwareId', displayName: 'Mac Address'},
                     { field: 'deviceIp', displayName:'Ip Address'},
                    ];


 $scope.showAlert = function(ev) {
        $mdDialog.show(
          $mdDialog.alert()
            .parent(angular.element(document.querySelector('#popupContainer')))
            .clickOutsideToClose(true)
            .title('Error!!!')
            .textContent("Error occured!!!")
            .ariaLabel('')
            .ok('Ok')
            .targetEvent(ev)
        );
    };

 
 
   
    
    
    
    
    
    
    
    
    $scope.getDeviceList = function () {
        HomeService.getDeviceList()
          .then (function success(response){ 
              $scope.deviceDetails = response.data;
              $scope.deviceName = response.data.deviceName;
              $scope.errorMessage = '';
          },
          function error(response){
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(true)
                  .title('Error occured!!!!!!')
                  .textContent(response.data.message)
                  .ariaLabel('')
                  .ok('Ok')
            );
        });
    }
    

    $scope.getDevice = function (devHardwareId) {
        HomeService.getDevice(devHardwareId)
          .then (function success(response){ 
              $scope.name = response.data.deviceName;
              $scope.hardwareId = response.data.devHardwareId;
              $scope.deviceIp = response.data.deviceIp;
              $scope.devStatus = response.data.isActive;
              $scope.errorMessage = '';
          },
          function error(response){
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(true)
                  .title('Error occured!!!!!!')
                  .textContent(response.data.message)
                  .ariaLabel('')
                  .ok('Ok')
            );
        });
    }


    $scope.getPeripheralList = function (devHardwareId){
        HomeService.getPeripheralList(devHardwareId)
        .then (function success(response){
            $scope.peripheralDetails = response.data;
            $scope.errorMessage = '';
        },
        function error(response) {
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(true)
                  .title('Error occured!!!!!!')
                  .textContent(response.data.message)
                  .ariaLabel('')
                  .ok('Ok')
            );
        });
    }


    $scope.getPeripheral = function (devHardwareId,perHardwareId){
        HomeService.getPeripheral(devHardwareId,perHardwareId)
        .then (function success(response){
            $scope.peripheralName = response.data.peripheralName;
            $scope.perStatus = response.data.isActive;
            $scope.peripheralType = response.data.peripheralType;
            $scope.hardwareId= responce.data.perHardwareId;

        },
        function error(response) {
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(true)
                  .title('Error occured!!!!!!')
                  .textContent(response.data.message)
                  .ariaLabel('')
                  .ok('Ok')
              );
            
        });
    }


    $scope.peripheralRequest = function (devHardwareId,perHardwareId,perRequest){
        var json = {"requestType":perRequest}
        HomeService.peripheralRequest(devHardwareId,perHardwareId,json)
        .then (function success(response) {
            $scope.message = 'request sent!!!';
            $scope.errorMessage = '';    
        },
        function error(response) {
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(true)
                  .title('Error occured!!!!!!')
                  .textContent(response.data.message)
                  .ariaLabel('')
                  .ok('Ok')
              );
         });
    }


    $scope.getCustomer = function(){
        HomeService.getCustomer()
        .then (function success(response){
            $scope.customerName = response.data.customerName;
            $scope.email = response.data.email;
            $scope.lastModifiedDate = response.data.lastModifiedDate;
            $scope.creationDate = response.data.creationDate;
        })
    }

    $scope.updateCustomer = function(){
       HomeService.updateCustomer($scope.update)
        .then (function success(response){ 
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(false)
                  .escapeToClose(false)
                  .textContent("Username has been changed successfully!")
                  .ariaLabel('')
                  .ok('Ok')
              );
              $mdDialog.hide()
              .onRemoving(window.location.reload())
              
        },
        function error(response){
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(false)
                  .textContent("Something is wrong!")
                  .ariaLabel('')
                  .ok('Ok')
              );             
      });
    } 
    

    $scope.changePassword = function() {
        HomeService.changePassword($scope.update)
        .then (function success(response){
            $mdDialog.show(
                $mdDialog.alert()
                  .parent(angular.element(document.querySelector('#popupContainer')))
                  .clickOutsideToClose(false)
                  .escapeToClose(false)
                  .textContent("Password has been changed successfully!")
                  .ariaLabel('')
                  .ok('Ok')                  
              );
              $mdDialog.hide()
                .onRemoving(document.logoutForm.submit())
              
              
        },
    function error(response){
        $mdDialog.show(
            $mdDialog.alert()
              .parent(angular.element(document.querySelector('#popupContainer')))
              .clickOutsideToClose(false)
              .textContent("Something is wrong!")
              .ariaLabel('')
              .ok('Ok')
          );
    });
    }


    App.directive('passwordConfirm', ['$parse', function ($parse) {
        return {
           restrict: 'A',
           scope: {
             matchTarget: '=',
           },
           require: 'ngModel',
           link: function link(scope, elem, attrs, ctrl) {
             var validator = function (value) {
               ctrl.$setValidity('match', value === scope.matchTarget);
               return value;
             }
        
             ctrl.$parsers.unshift(validator);
             ctrl.$formatters.push(validator);
             
             // This is to force validator when the original password gets changed
             scope.$watch('matchTarget', function(newval, oldval) {
               validator(ctrl.$viewValue);
             });
       
           }
         };
         
       }]);
      

}]);


