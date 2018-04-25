import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Secteur } from './secteur.model';
import { SecteurPopupService } from './secteur-popup.service';
import { SecteurService } from './secteur.service';
import { Tag, TagService } from '../tag';

@Component({
    selector: 'jhi-secteur-dialog',
    templateUrl: './secteur-dialog.component.html'
})
export class SecteurDialogComponent implements OnInit {

    secteur: Secteur;
    isSaving: boolean;

    tags: Tag[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private secteurService: SecteurService,
        private tagService: TagService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tagService.query()
            .subscribe((res: HttpResponse<Tag[]>) => { this.tags = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.secteur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.secteurService.update(this.secteur));
        } else {
            this.subscribeToSaveResponse(
                this.secteurService.create(this.secteur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Secteur>>) {
        result.subscribe((res: HttpResponse<Secteur>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Secteur) {
        this.eventManager.broadcast({ name: 'secteurListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-secteur-popup',
    template: ''
})
export class SecteurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private secteurPopupService: SecteurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.secteurPopupService
                    .open(SecteurDialogComponent as Component, params['id']);
            } else {
                this.secteurPopupService
                    .open(SecteurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
