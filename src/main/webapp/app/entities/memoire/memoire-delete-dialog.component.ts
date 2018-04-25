import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Memoire } from './memoire.model';
import { MemoirePopupService } from './memoire-popup.service';
import { MemoireService } from './memoire.service';

@Component({
    selector: 'jhi-memoire-delete-dialog',
    templateUrl: './memoire-delete-dialog.component.html'
})
export class MemoireDeleteDialogComponent {

    memoire: Memoire;

    constructor(
        private memoireService: MemoireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.memoireService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'memoireListModification',
                content: 'Deleted an memoire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-memoire-delete-popup',
    template: ''
})
export class MemoireDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private memoirePopupService: MemoirePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.memoirePopupService
                .open(MemoireDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
