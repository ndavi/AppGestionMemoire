/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { PromotionComponent } from '../../../../../../main/webapp/app/entities/promotion/promotion.component';
import { PromotionService } from '../../../../../../main/webapp/app/entities/promotion/promotion.service';
import { Promotion } from '../../../../../../main/webapp/app/entities/promotion/promotion.model';

describe('Component Tests', () => {

    describe('Promotion Management Component', () => {
        let comp: PromotionComponent;
        let fixture: ComponentFixture<PromotionComponent>;
        let service: PromotionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [PromotionComponent],
                providers: [
                    PromotionService
                ]
            })
            .overrideTemplate(PromotionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PromotionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PromotionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Promotion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.promotions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
