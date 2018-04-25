/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { PromotionDetailComponent } from '../../../../../../main/webapp/app/entities/promotion/promotion-detail.component';
import { PromotionService } from '../../../../../../main/webapp/app/entities/promotion/promotion.service';
import { Promotion } from '../../../../../../main/webapp/app/entities/promotion/promotion.model';

describe('Component Tests', () => {

    describe('Promotion Management Detail Component', () => {
        let comp: PromotionDetailComponent;
        let fixture: ComponentFixture<PromotionDetailComponent>;
        let service: PromotionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [PromotionDetailComponent],
                providers: [
                    PromotionService
                ]
            })
            .overrideTemplate(PromotionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PromotionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PromotionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Promotion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.promotion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
