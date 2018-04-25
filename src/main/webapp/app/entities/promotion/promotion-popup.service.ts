import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Promotion } from './promotion.model';
import { PromotionService } from './promotion.service';

@Injectable()
export class PromotionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private promotionService: PromotionService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.promotionService.find(id)
                    .subscribe((promotionResponse: HttpResponse<Promotion>) => {
                        const promotion: Promotion = promotionResponse.body;
                        promotion.datedebut = this.datePipe
                            .transform(promotion.datedebut, 'yyyy-MM-ddTHH:mm:ss');
                        promotion.datefin = this.datePipe
                            .transform(promotion.datefin, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.promotionModalRef(component, promotion);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.promotionModalRef(component, new Promotion());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    promotionModalRef(component: Component, promotion: Promotion): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.promotion = promotion;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
