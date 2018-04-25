import { BaseEntity } from './../../shared';

export class Specialite implements BaseEntity {
    constructor(
        public id?: number,
        public libelle?: string,
        public specialites?: BaseEntity[],
        public secteurId?: number,
    ) {
    }
}
