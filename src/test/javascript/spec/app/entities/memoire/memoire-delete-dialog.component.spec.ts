/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { MemoireDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/memoire/memoire-delete-dialog.component';
import { MemoireService } from '../../../../../../main/webapp/app/entities/memoire/memoire.service';

describe('Component Tests', () => {

    describe('Memoire Management Delete Component', () => {
        let comp: MemoireDeleteDialogComponent;
        let fixture: ComponentFixture<MemoireDeleteDialogComponent>;
        let service: MemoireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [MemoireDeleteDialogComponent],
                providers: [
                    MemoireService
                ]
            })
            .overrideTemplate(MemoireDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MemoireDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MemoireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
