import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Promotion } from './promotion.model';
import { PromotionPopupService } from './promotion-popup.service';
import { PromotionService } from './promotion.service';
import { Specialite, SpecialiteService } from '../specialite';

@Component({
    selector: 'jhi-promotion-dialog',
    templateUrl: './promotion-dialog.component.html'
})
export class PromotionDialogComponent implements OnInit {

    promotion: Promotion;
    isSaving: boolean;

    specialites: Specialite[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private promotionService: PromotionService,
        private specialiteService: SpecialiteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.specialiteService.query()
            .subscribe((res: HttpResponse<Specialite[]>) => { this.specialites = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.promotion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.promotionService.update(this.promotion));
        } else {
            this.subscribeToSaveResponse(
                this.promotionService.create(this.promotion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Promotion>>) {
        result.subscribe((res: HttpResponse<Promotion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Promotion) {
        this.eventManager.broadcast({ name: 'promotionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSpecialiteById(index: number, item: Specialite) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-promotion-popup',
    template: ''
})
export class PromotionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private promotionPopupService: PromotionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.promotionPopupService
                    .open(PromotionDialogComponent as Component, params['id']);
            } else {
                this.promotionPopupService
                    .open(PromotionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
