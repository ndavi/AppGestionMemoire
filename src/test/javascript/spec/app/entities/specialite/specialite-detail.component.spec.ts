/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { SpecialiteDetailComponent } from '../../../../../../main/webapp/app/entities/specialite/specialite-detail.component';
import { SpecialiteService } from '../../../../../../main/webapp/app/entities/specialite/specialite.service';
import { Specialite } from '../../../../../../main/webapp/app/entities/specialite/specialite.model';

describe('Component Tests', () => {

    describe('Specialite Management Detail Component', () => {
        let comp: SpecialiteDetailComponent;
        let fixture: ComponentFixture<SpecialiteDetailComponent>;
        let service: SpecialiteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [SpecialiteDetailComponent],
                providers: [
                    SpecialiteService
                ]
            })
            .overrideTemplate(SpecialiteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpecialiteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialiteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Specialite(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.specialite).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
