/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { MemoireComponent } from '../../../../../../main/webapp/app/entities/memoire/memoire.component';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire/memoire.service';
import { Memoire } from '../../../../../../main/webapp/app/entities/memoire/memoire.model';

describe('Component Tests', () => {

    describe('Memoire Management Component', () => {
        let comp: MemoireComponent;
        let fixture: ComponentFixture<MemoireComponent>;
        let service: MemoireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [MemoireComponent],
                providers: [
                    MemoireService
                ]
            })
            .overrideTemplate(MemoireComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MemoireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemoireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Memoire(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.memoires[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
