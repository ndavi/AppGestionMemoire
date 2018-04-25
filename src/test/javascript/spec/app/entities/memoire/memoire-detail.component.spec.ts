/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { MemoireDetailComponent } from '../../../../../../main/webapp/app/entities/memoire/memoire-detail.component';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire/memoire.service';
import { Memoire } from '../../../../../../main/webapp/app/entities/memoire/memoire.model';

describe('Component Tests', () => {

    describe('Memoire Management Detail Component', () => {
        let comp: MemoireDetailComponent;
        let fixture: ComponentFixture<MemoireDetailComponent>;
        let service: MemoireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [MemoireDetailComponent],
                providers: [
                    MemoireService
                ]
            })
            .overrideTemplate(MemoireDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MemoireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemoireService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Memoire(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.memoire).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
