    'use strict';


    App.controller('RegistrationController', ['$scope', 'RegistrationService',"$mdDialog", function($scope, RegistrationService,$mdDialog) {

        $scope.password = null;
        $scope.passwordConfirmation = null;


        $scope.showAlert = function(ev) {
          $mdDialog.show(
            $mdDialog.alert()
              .parent(angular.element(document.querySelector('#popupContainer')))
              .clickOutsideToClose(true)
              .title('Error!!!')
              .textContent("Error occured!")
              .ariaLabel('')
              .ok('Ok')
              .targetEvent(ev)
          );
      };

 
    $scope.createCustomer = function () {
            RegistrationService.createCustomer($scope.customer)
              .then (function success(response){ 
                if(response.data.status=="OK")
                {
                $mdDialog.show(
                  $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('Error Occured!')
                    .textContent("Email has been already taken!")
                    .ariaLabel('')
                    .ok('Ok')
                );
              }
              else{
                $mdDialog.show(
                  $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('Successfull!')
                    .textContent("Account has been created successfully!")
                    .ariaLabel('')
                    .ok('Ok')
                );
              }
              },
              function error(response){
                $mdDialog.show(
                  $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('Error occured!')
                    .textContent(response.data.message)
                    .ariaLabel('')
                    .ok('Ok')
                  );
            });
        }

}]);


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


   

  