import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Secteur } from './secteur.model';
import { SecteurPopupService } from './secteur-popup.service';
import { SecteurService } from './secteur.service';

@Component({
    selector: 'jhi-secteur-delete-dialog',
    templateUrl: './secteur-delete-dialog.component.html'
})
export class SecteurDeleteDialogComponent {

    secteur: Secteur;

    constructor(
        private secteurService: SecteurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.secteurService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'secteurListModification',
                content: 'Deleted an secteur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-secteur-delete-popup',
    template: ''
})
export class SecteurDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private secteurPopupService: SecteurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.secteurPopupService
                .open(SecteurDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
