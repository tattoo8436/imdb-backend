export const initSearch = (search: any) => {
  search['page'] = search.page ?? 1;
  search['limit'] = search.limit ?? 10;
  search['sortBy'] = search.sortBy ?? 'id';
  search['orderBy'] = search.orderBy ?? 'ASC';
  return search;
};
