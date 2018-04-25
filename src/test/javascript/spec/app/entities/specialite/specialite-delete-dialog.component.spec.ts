/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AppGestionMemoireTestModule } from '../../../test.module';
import { SpecialiteDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/specialite/specialite-delete-dialog.component';
import { SpecialiteService } from '../../../../../../main/webapp/app/entities/specialite/specialite.service';

describe('Component Tests', () => {

    describe('Specialite Management Delete Component', () => {
        let comp: SpecialiteDeleteDialogComponent;
        let fixture: ComponentFixture<SpecialiteDeleteDialogComponent>;
        let service: SpecialiteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppGestionMemoireTestModule],
                declarations: [SpecialiteDeleteDialogComponent],
                providers: [
                    SpecialiteService
                ]
            })
            .overrideTemplate(SpecialiteDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpecialiteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpecialiteService);
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
