import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Specialite } from './specialite.model';
import { SpecialitePopupService } from './specialite-popup.service';
import { SpecialiteService } from './specialite.service';

@Component({
    selector: 'jhi-specialite-delete-dialog',
    templateUrl: './specialite-delete-dialog.component.html'
})
export class SpecialiteDeleteDialogComponent {

    specialite: Specialite;

    constructor(
        private specialiteService: SpecialiteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.specialiteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'specialiteListModification',
                content: 'Deleted an specialite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-specialite-delete-popup',
    template: ''
})
export class SpecialiteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private specialitePopupService: SpecialitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.specialitePopupService
                .open(SpecialiteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
