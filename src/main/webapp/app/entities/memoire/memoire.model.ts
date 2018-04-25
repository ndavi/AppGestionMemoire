import { BaseEntity } from './../../shared';

export const enum Langue {
    'FRANCAIS',
    'ANGLAIS'
}

export class Memoire implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public sujet?: string,
        public langue?: Langue,
        public confidentiel?: boolean,
        public tags?: BaseEntity[],
    ) {
        this.confidentiel = false;
    }
}
