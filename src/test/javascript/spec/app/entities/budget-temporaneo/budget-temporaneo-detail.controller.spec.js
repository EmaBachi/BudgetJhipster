'use strict';

describe('Controller Tests', function() {

    describe('BudgetTemporaneo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBudgetTemporaneo, MockCommessa, MockContoContabile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBudgetTemporaneo = jasmine.createSpy('MockBudgetTemporaneo');
            MockCommessa = jasmine.createSpy('MockCommessa');
            MockContoContabile = jasmine.createSpy('MockContoContabile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'BudgetTemporaneo': MockBudgetTemporaneo,
                'Commessa': MockCommessa,
                'ContoContabile': MockContoContabile
            };
            createController = function() {
                $injector.get('$controller')("BudgetTemporaneoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'budgetApp:budgetTemporaneoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
