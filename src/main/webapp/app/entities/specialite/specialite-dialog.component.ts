import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Specialite } from './specialite.model';
import { SpecialitePopupService } from './specialite-popup.service';
import { SpecialiteService } from './specialite.service';
import { Secteur, SecteurService } from '../secteur';

@Component({
    selector: 'jhi-specialite-dialog',
    templateUrl: './specialite-dialog.component.html'
})
export class SpecialiteDialogComponent implements OnInit {

    specialite: Specialite;
    isSaving: boolean;

    secteurs: Secteur[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private specialiteService: SpecialiteService,
        private secteurService: SecteurService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.secteurService.query()
            .subscribe((res: HttpResponse<Secteur[]>) => { this.secteurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.specialite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.specialiteService.update(this.specialite));
        } else {
            this.subscribeToSaveResponse(
                this.specialiteService.create(this.specialite));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Specialite>>) {
        result.subscribe((res: HttpResponse<Specialite>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Specialite) {
        this.eventManager.broadcast({ name: 'specialiteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSecteurById(index: number, item: Secteur) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-specialite-popup',
    template: ''
})
export class SpecialitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private specialitePopupService: SpecialitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.specialitePopupService
                    .open(SpecialiteDialogComponent as Component, params['id']);
            } else {
                this.specialitePopupService
                    .open(SpecialiteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
