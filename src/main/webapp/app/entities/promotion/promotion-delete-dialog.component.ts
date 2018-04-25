import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Promotion } from './promotion.model';
import { PromotionPopupService } from './promotion-popup.service';
import { PromotionService } from './promotion.service';

@Component({
    selector: 'jhi-promotion-delete-dialog',
    templateUrl: './promotion-delete-dialog.component.html'
})
export class PromotionDeleteDialogComponent {

    promotion: Promotion;

    constructor(
        private promotionService: PromotionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.promotionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'promotionListModification',
                content: 'Deleted an promotion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-promotion-delete-popup',
    template: ''
})
export class PromotionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private promotionPopupService: PromotionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.promotionPopupService
                .open(PromotionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
