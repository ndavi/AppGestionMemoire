/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { SecteurDetailComponent } from '../../../../../../main/webapp/app/entities/secteur/secteur-detail.component';
import { SecteurService } from '../../../../../../main/webapp/app/entities/secteur/secteur.service';
import { Secteur } from '../../../../../../main/webapp/app/entities/secteur/secteur.model';

describe('Component Tests', () => {

    describe('Secteur Management Detail Component', () => {
        let comp: SecteurDetailComponent;
        let fixture: ComponentFixture<SecteurDetailComponent>;
        let service: SecteurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [SecteurDetailComponent],
                providers: [
                    SecteurService
                ]
            })
            .overrideTemplate(SecteurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SecteurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SecteurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Secteur(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.secteur).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
