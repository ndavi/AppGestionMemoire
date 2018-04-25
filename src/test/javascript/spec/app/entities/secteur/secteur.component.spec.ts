/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { SecteurComponent } from '../../../../../../main/webapp/app/entities/secteur/secteur.component';
import { SecteurService } from '../../../../../../main/webapp/app/entities/secteur/secteur.service';
import { Secteur } from '../../../../../../main/webapp/app/entities/secteur/secteur.model';

describe('Component Tests', () => {

    describe('Secteur Management Component', () => {
        let comp: SecteurComponent;
        let fixture: ComponentFixture<SecteurComponent>;
        let service: SecteurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [SecteurComponent],
                providers: [
                    SecteurService
                ]
            })
            .overrideTemplate(SecteurComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SecteurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SecteurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Secteur(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.secteurs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
