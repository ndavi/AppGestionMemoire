/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { SpecialiteComponent } from '../../../../../../main/webapp/app/entities/specialite/specialite.component';
import { SpecialiteService } from '../../../../../../main/webapp/app/entities/specialite/specialite.service';
import { Specialite } from '../../../../../../main/webapp/app/entities/specialite/specialite.model';

describe('Component Tests', () => {

    describe('Specialite Management Component', () => {
        let comp: SpecialiteComponent;
        let fixture: ComponentFixture<SpecialiteComponent>;
        let service: SpecialiteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [SpecialiteComponent],
                providers: [
                    SpecialiteService
                ]
            })
            .overrideTemplate(SpecialiteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpecialiteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialiteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Specialite(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.specialites[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
